const mongoose = require('mongoose');

const jobSchema = new mongoose.Schema({
  jobName: String,
  user: String
});

const Job = mongoose.model('Job', jobSchema);
module.exports = Job;
