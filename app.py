from flask import Flask, request

app = Flask(__name__)

@app.route('/search')
def search():
    term = request.args.get('term', '')  # Get the 'term' query parameter
    return f'You searched for: {term}'

if __name__ == '__main__':
    app.run(debug=True)
``
