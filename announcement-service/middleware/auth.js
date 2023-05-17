const jwt = require("jsonwebtoken");
const config = require("config");

module.exports = function (req, res, next) {
  const bearerHeader = req.headers["authorization"];
  if (typeof bearerHeader !== "undefined") {
    const bearer = bearerHeader.split(" ");
    const bearerToken = bearer[1];
    try {
      console.log(req.route.path);
      console.log(req);
      const decoded = jwt.verify(
        bearerToken,
        Buffer.from(process.env.jwtPrivateKey, "base64")
      );
      console.log(decoded);
      // console.log(decoded.roles.includes("viewAttendance"));
      req.user = decoded;
      if (
        req.route.path === "/" &&
        decoded.roles.includes("viewAnnouncement")
      ) {
        next();
      } else if (
        req.route.path === "/:id" &&
        decoded.roles.includes("viewAnnouncement")
      ) {
        next();
      } else if (
        req.route.path === "/create" &&
        decoded.roles.includes("updateAnnouncement")
      ) {
        next();
      } else {
        return res.status(403).send("Access denied.");
      }
    } catch (ex) {
      console.log(ex);
      return res.status(400).send("Invalid token");
    }
  } else {
    return res.status(401).send("Access denied. No token provided");
  }
};
