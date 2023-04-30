const express = require("express");
const auth = require("../middleware/auth");
const { Reply, validateReply } = require("../models/replies");
const _ = require("lodash");
const { Post } = require("../models/post");
const router = express.Router();

router.post("/create-post-comment/:id", auth, async (req, res) => {
  try {
    const post = await Post.findById(req.params.id);
    if (!post) {
      throw "";
    }
  } catch (ex) {
    return res.status(400).send("The Post with given ID doesn't exists!");
  }
  const { error } = validateReply(req.body);
  if (error) res.status(400).send(error.details[0].message);
  const reply = new Reply({
    post: req.params.id,
    comment: req.body.comment,
    author: req.user.userId,
  });
  try {
    await reply.save();
    const reply_populated = await Reply.find({ _id: reply._id });
    res.send(reply_populated);
  } catch (ex) {
    console.log("error: ", ex);
  }
});

router.post("/create-reply-comment/:id", auth, async (req, res) => {
  try {
    const post = await Reply.findById(req.params.id);
    if (!post) {
      throw "";
    }
  } catch (ex) {
    return res.status(400).send("The Reply with given ID doesn't exists!");
  }
  const { error } = validateReply(req.body);
  if (error) res.status(400).send(error.details[0].message);
  const reply = new Reply({
    post: req.params.id,
    comment: req.body.comment,
    author: req.user.userId,
  });
  try {
    await reply.save();
    const reply_populated = await Reply.find({ _id: reply._id });
    res.send(reply_populated);
  } catch (ex) {
    console.log("error: ", ex);
  }
});

router.get("/:id", async (req, res) => {
  try {
    const post = await Post.findById(req.params.id);
    if (!post) {
      throw "";
    }
  } catch (ex) {
    return res.status(400).send("The Post with given ID doesn't exists!");
  }
  const replies = await Reply.find({ post: req.params.id });
  res.send(replies);
});
router.get("/comment/:id", async (req, res) => {
  try {
    const post = await Reply.findById(req.params.id);
    if (!post) {
      throw "";
    }
  } catch (ex) {
    return res.status(400).send("The Reply with given ID doesn't exists!");
  }
  const replies = await Reply.find({ post: req.params.id });
  res.send(replies);
});

router.put("/like/:id", auth, async (req, res) => {
  const reply = await Reply.findById(req.params.id);
  if (!reply) return res.status(400).send("reply doesn't exists");
  if (reply.author == req.user._id)
    return res.status(400).send("You can't upvote your own reply");
  const upvoteArray = reply.upvotes;
  const index = upvoteArray.indexOf(req.user._id);
  if (index === -1) {
    upvoteArray.push(req.user._id);
  } else {
    upvoteArray.splice(index, 1);
  }
  reply.upvotes = upvoteArray;
  const result = await reply.save();
  const reply_new = await Reply.find({ _id: reply._id });
  res.send(reply_new);
});

module.exports = router;
