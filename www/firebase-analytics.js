var exec = require('cordova/exec');

module.exports = {
  logEvent: function(eventName, eventParams) {
    return new Promise(
      function(resolve, reject) {
        exec(resolve, reject, 'FirebaseAnalyticsPlugin', 'logEvent', [eventName, eventParams]);
      }
    );
  },
  setUserId: function(userId) {
    return new Promise(
      function(resolve, reject) {
        exec(resolve, reject, 'FirebaseAnalyticsPlugin', 'setUserId', [userId]);
      }
    );
  }
};
