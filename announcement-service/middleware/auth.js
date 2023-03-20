const jwt = require("jsonwebtoken");
const config = require("config");

module.exports = function(req, res, next) {
  const bearerHeader = req.headers['authorization'];
  if(typeof bearerHeader !== 'undefined'){
    const bearer=bearerHeader.split(' ');
    const bearerToken=bearer[1];
    try {
      const decoded = jwt.verify(bearerToken, Buffer.from(process.env.jwtPrivateKey,"base64"));
      console.log(decoded);
      req.user = decoded;
      next();
    } catch (ex) {
      console.log(ex)
      return res.status(400).send("Invalid token");
    }

  }
  else{
    return res.status(401).send("Access denied. No token provided");
  } 
  
};
