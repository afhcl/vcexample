const express = require('express');
const app = express();
const port = 3000;

// Route for /search
app.get('/search', (req, res) => {
    const term = req.query.term; // Get the 'term' query parameter
    res.send(`You searched for: ${term}`);
});

app.listen(port, () => {
    console.log(`Server running at http://localhost:${port}`);
});
