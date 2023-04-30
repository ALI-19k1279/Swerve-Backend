const mongoose = require("mongoose");
const { User } = require("./user");
const Joi = require("joi");
const { tagSchema } = require("./tag");

const announcementSchema = new mongoose.Schema({
  title: {
    type: String,
    required: true,
    minlength: 10,
    maxlength: 80,
  },
  recepients: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
    minlength: 5,
    maxlength: 1024,
    required: true,
  },
  author: {
    type: String,
    required: true,
  },
  time: {
    type: Date,
    default: Date.now,
  },
  // activeUntil: {
  //   type: Date,
  //   required:true,
  // },
});

const Announcement = mongoose.model("Announcement", announcementSchema);

function validateAnnouncement(announcement) {
  const schema = Joi.object({
    title: Joi.string().required(),
    description: Joi.string().required().min(3).max(1024),
    recepients: Joi.string(),
    // activeUntil: Joi.date(),
  });
  return schema.validate(announcement);
}

exports.Announcement = Announcement;
exports.validateAnnouncement = validateAnnouncement;

// async function listPosts() {
//   const users = await Post.find().select().populate('author');
//   console.log(users);
// }

//listPosts();

// async function CreatePost() {
//   const a = new Post({
//     title: 'Should I learn Web Dev?',
//     description: 'The title explains it all',
//     author: '6012bd5feff00735ffd93f83'
//   });
//   await a.save();
// }

//CreatePost();

//listPosts();
