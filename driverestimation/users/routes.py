from flask import request, Blueprint, jsonify, Response
from flask_login import login_user, current_user, logout_user, login_required
from driverestimation import db, bcrypt
from driverestimation.models import User
from driverestimation.users.utils import send_reset_email, send_confirm_email

users = Blueprint('users', __name__)


@users.route("/register", methods=['GET', 'POST'])
def register():
    if current_user.is_authenticated:
        return Response(status=301)
    if request.method == 'POST':
        user_json = request.get_json(force=True)
        if User.query.filter_by(email=user_json['email']).first() is None:
            hashed_password = bcrypt.generate_password_hash(user_json['password']).decode('utf-8')
            user = User(username=user_json['username'], email=user_json['email'], password=hashed_password,
                        car=user_json['car'], confirmed=False)
            db.session.add(user)
            db.session.commit()
            send_confirm_email(user)
            return Response(status=200)
        else:
            return Response(status=417)


@users.route('/confirm/<token>')
def confirm_email(token):
    try:
        email = User.verify_confirm_token(token)
    except:
        return Response(status=404)
    user = User.query.filter_by(email=email).first_or_404()
    if user.confirmed:
        return Response(status=301)
    else:
        user.confirmed = True
        db.session.add(user)
        db.session.commit()
        return Response(status=200)


@users.route("/login", methods=['GET', 'POST'])
def login():
    if current_user.is_authenticated:
        return Response(status=301)
    if request.method == 'POST':
        user_json = request.get_json()
        user = User.query.filter_by(email=user_json['email']).first()
        if user and bcrypt.check_password_hash(user.password, user_json['password']) and user.confirmed:
            login_user(user, remember=True)  # хз, надо или нет
            return Response(status=200)
        else:
            return Response(status=403)


@users.route("/logout")
def logout():
    logout_user()
    return Response(status=200)


@users.route("/account", methods=['GET', 'POST'])
@login_required
def account():
    if request.method == 'POST':
        user_json = request.get_json()
        if current_user.email != user_json['email'] and User.query.filter_by(email=user_json['email']).first() is not None:
            return Response(status=417)
        else:
            current_user.username = user_json['username']
            current_user.email = user_json['email']
            current_user.car = user_json['car']
            current_user.image_file = user_json['image_file']
            db.session.commit()
            return Response(status=200)
    elif request.method == 'GET':
        return jsonify(username=current_user.username, email=current_user.email,
                       car=current_user.car)


@users.route("/reset_password", methods=['GET', 'POST'])
def reset_request():
    if current_user.is_authenticated:
        return Response(status=301)
    if request.method == 'POST':
        user_json = request.get_json()
        user = User.query.filter_by(email=user_json['email']).first()
        if user is not None:
            send_reset_email(user)
            return Response(status=200)
        else:
            return Response(status=404)


@users.route("/reset_password/<token>", methods=['GET', 'POST'])
def reset_token(token):
    if current_user.is_authenticated:
        return Response(status=301)
    user = User.verify_reset_token(token)
    if user is None:
        return Response(status=404)

    if request.method == 'POST':
        user_json = request.get_json()
        hashed_password = bcrypt.generate_password_hash(user_json['password']).decode('utf-8')
        user.password = hashed_password
        db.session.commit()
        return Response(status=200)
