const path = require('path');
module.exports = {
  module: {
    rules: [{
      test: /\.(ttf)$/,
      use: [ {
        loader: 'file-loader',
        options: {
          name: '[name].[ext]',
          outputPath: 'fonts/',
          publicPath: '/fonts/', // 添加 publicPath 配置
          context: path.resolve(__dirname, '../asset/fonts/') // 添加 context 配置
        }
      }
    ] }]
  }
}