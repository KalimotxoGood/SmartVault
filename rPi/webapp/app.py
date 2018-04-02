from flask import Flask, render_template

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/hello/<name>')
def hello(name):
    return render_template('page.html', name=name)

@app.route('/cakes')
def cakes():
    a = "hello eveyrthing" ;
    return a;


if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0',port=5000)




