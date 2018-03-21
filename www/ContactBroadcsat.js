var exec = require('cordova/exec');

var PLUGIN_NAME = 'ContactBroadcsat';

var ContactBroadcsat = {
    eventContact: function(phrase, successCallback, errorCallback) {
        exec(successCallback, errorCallback, PLUGIN_NAME, 'eventContact', [phrase]);
    }
};

module.exports = ContactBroadcsat;