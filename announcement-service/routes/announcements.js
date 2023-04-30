const express = require("express");
const router = express.Router();
const _ = require("lodash");
const {
  Announcement,
  validateAnnouncement,
} = require("../models/announcement");
const { User } = require("../models/user");
const auth = require("../middleware/auth");
const { Tag } = require("../models/tag");

router.get("/", async (req, res) => {
  let all_announcements = await Announcement.find();
  res.send(all_announcements);
});

router.get("/:id", async (req, res) => {
  try {
    const announcement = await Announcement.find({ _id: req.params.id });
    res.send(announcement[0]);
  } catch (ex) {
    return res.send(ex.message);
  }
});

router.post("/create", auth, async (req, res) => {
  console.log("here");
  const { error } = validateAnnouncement(req.body);
  if (error) return res.status(400).send(error.details[0].message);
  // const recepients = req.body.recepients;
  // const tags_array = [];
  // for (let i = 0; i < tags.length; i++) {
  //   const tag_in_db = await Tag.findById(tags[i]);
  //   if (!tag_in_db) return res.status(400).send("Invalid Tag");
  //   tags_array.push(tag_in_db);
  // }
  const announcement = new Announcement({
    title: req.body.title,
    recepients: req.body.recepients,
    description: req.body.description,
    author: req.user.userId,
    // activeUntil: req.body.activeUntil,
  });
  try {
    await announcement.save();
    console.log(announcement);
    res.send("Announcement succesfully created.");
  } catch (err) {
    console.log("dsaf");
    console.log("error: ", err);
  }
});

// router.put("/like/:id", auth, async (req, res) => {
//   const post = await Post.findById(req.params.id);
//   if (!post) return res.status(400).send("Post doesn't exists");
//   if (post.author == req.user._id)
//     return res.status(400).send("You can't upvote your own post");
//   const upvoteArray = post.upvotes;
//   const index = upvoteArray.indexOf(req.user._id);
//   if (index === -1) {
//     upvoteArray.push(req.user._id);
//   } else {
//     upvoteArray.splice(index, 1);
//   }
//   post.upvotes = upvoteArray;
//   const result = await post.save();
//   const post_new = await Post.find({ _id: post._id }).populate(
//     "author",
//     "name username"
//   );
//   res.send(post_new);
// });

module.exports = router;
