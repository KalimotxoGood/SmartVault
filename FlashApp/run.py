from app import app
HOST='192.168.0.100'

#Starting on my server, our ip address may be different.

app.run(host=HOST, debug=True)
