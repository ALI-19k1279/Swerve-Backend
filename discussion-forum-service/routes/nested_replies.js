const express = require("express");
const auth = require("../middleware/auth");
const { NestedReply, validateNestedReply } = require("../models/nested_replies");
const _ = require("lodash");
const { Reply } = require("../models/replies");
const router = express.Router();

router.post("/create/:id", auth, async (req, res) => {
  console.log("here");
  try {
    console.log("here");
    const post = await Reply.findById(req.params.id);
  } catch (ex) {
    return res.status(400).send("The Reply with given ID doesn't exists!");
  }
  const { error } = validateNestedReply(req.body);
  if (error) res.status(400).send(error.details[0].message);
  const nestedReply = new NestedReply({
    reply: req.params.id,
    comment: req.body.comment,
    author: req.user._id,
  });
  try {
    await nestedReply.save();
    const nestedReply_populated = await NestedReply.find({ _id: nestedReply._id }).populate(
      "author",
      "name -_id"
    );
    res.send(nestedReply_populated);
  } catch (ex) {
    console.log("error: ", ex);
  }
});

router.get("/:id", async (req, res) => {
  try {
    const post = await Reply.findById(req.params.id);
  } catch (ex) {
    return res.status(400).send("The Reply with given ID doesn't exists!");
  }
  const nestedReplies = await NestedReply.find({ reply: req.params.id }).populate(
    "author",
    "name username"
  );
  res.send(nestedReplies);
});

router.put("/like/:id", auth, async (req, res) => {
  const nestedReply = await NestedReply.findById(req.params.id);
  if (!nestedReply) return res.status(400).send("reply doesn't exists");
  if (nestedReply.author == req.user._id)
    return res.status(400).send("You can't upvote your own reply");
  const upvoteArray = nestedReply.upvotes;
  const index = upvoteArray.indexOf(req.user._id);
  if (index === -1) {
    upvoteArray.push(req.user._id);
  } else {
    upvoteArray.splice(index, 1);
  }
  nestedReply.upvotes = upvoteArray;
  const result = await nestedReply.save();
  const nestedReply_new = await NestedReply.find({ _id: nestedReply._id }).populate(
    "author",
    "name username"
  );
  res.send(nestedReply_new);
});

module.exports = router;
