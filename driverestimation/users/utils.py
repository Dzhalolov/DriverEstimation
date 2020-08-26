import os
import secrets
from flask import url_for, current_app
from flask_mail import Message
from driverestimation import mail
from driverestimation.models import User


def send_reset_email(user):
    token = user.get_reset_token()
    msg = Message('Password Reset Request',
                  sender=current_app.config['MAIL_USERNAME'],
                  recipients=[user.email])
    msg.body = f'''To reset your password, visit the following link:
{url_for('users.reset_token', token=token, _external=True)}
If you did not make this request then simply ignore this email and no changes will be made.
'''
    mail.send(msg)


def send_confirm_email(user):
    token = user.get_confirm_token(user.email)
    msg = Message('Confirm email request',
                  sender=current_app.config['MAIL_USERNAME'],
                  recipients=[user.email])
    msg.body = f'''To finish the registration, visit the following link:
    {url_for('users.confirm_email', token=token, _external=True)}
    If you did not make this request then simply ignore this email.
    '''
    mail.send(msg)


