const path = require('path');

module.exports = {
  entry: {
    app: './js/addProduct.js',
  },
  output: {
    path: path.resolve(__dirname, 'dist'),
    clean: true,
    filename: './js/addProduct.js',
  },
};
