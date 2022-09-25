'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')
var localIp = "localhost"
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BACKEND_URL: '"http://' + localIp + ':8081"',
  BACKEND_SOCKET_URL: '"http://' + localIp + ':8082"'
})
