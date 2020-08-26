import os


class Config:
	SECRET_KEY = '5791628bb0b13ce0c676dfde280ba245'
	SECURITY_PASSWORD_SALT = '58e7389aa26a64d28b50c1498dc3da0d'
	SQLALCHEMY_DATABASE_URI = 'postgresql+psycopg2://postgre:test@db:5432/test'
	MAIL_SERVER = 'smtp.rambler.ru'
	MAIL_PORT = 465
	MAIL_USE_TLS = False
	MAIL_USE_SSL = True
	MAIL_USERNAME = os.environ.get('EMAIL_USER')
	MAIL_PASSWORD = os.environ.get('EMAIL_PASS')
