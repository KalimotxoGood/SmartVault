from flask import render_template
from app import app
gpio = 5000;
@app.route('/')
@app.route('/index')
def index():
	user = {'nickname':'Michael'} 
	return render_template('index.html',
				title='Home',
				user=user)

@app.route('/gpio')
def gpioReturn():
	return str(gpio)
