console.log('Server-side code running');

const express = require('express');
const multer = require("multer");
const fs = require('fs'); 

const app = express();
const { readFile } = require('fs').promises;

// serve files from the public directory
app.use(express.static('public'));

const addButtonCount = 0;
const searchButtonCount = 0;

app.get('/', async (request, response) => {
    // homepage
    response.send( await readFile(__dirname + '/index.html', 'utf8') );

});

const upload = multer({ dest: "files/"});

app.get('/form', async (request, response) => {
    // homepage
    response.send( await readFile(__dirname + '/public/form.html', 'utf8') );

});

const PORT = 8080;
const HOST = '0.0.0.0';

app.listen(PORT, HOST, () => {
    console.log(`Running on http://127.0.0.1:${PORT}`);
  });
