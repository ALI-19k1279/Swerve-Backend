const mongoose = require("mongoose");
const Joi = require("joi");

const nestedReplySchema = new mongoose.Schema({
  reply: {
    type: mongoose.Schema.ObjectId,
    required: true,
  },
  comment: {
    type: String,
    required: true,
    minlength: 3,
    maxlength: 5000,
  },
  author: {
    type: mongoose.Schema.ObjectId,
    ref: "User",
    required: true,
  },
  upvotes: {
    type: [mongoose.Schema.Types.ObjectId],
    ref: "User",
    default: [],
  },
  time: {
    type: Date,
    default: Date.now,
  },
});

const NestedReply = mongoose.model("NestedReply", nestedReplySchema);


// function to validate posts
function validateNestedReply(reply) {
  const schema = Joi.object({
    comment: Joi.string().required().min(3).max(5000),
  });
  return schema.validate(reply);
};


exports.NestedReply = NestedReply;
exports.validateNestedReply = validateNestedReply;
