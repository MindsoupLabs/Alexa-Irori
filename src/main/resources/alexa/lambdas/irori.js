var request = require('request');
var querystring = require('querystring');

var ip = '46.129.108.152';
var port = '8088';
var protocol = 'http';
var rootUri = protocol + '://' + ip + ':' + port + '/irori/';

exports.handler = (event, context) => {

    try {

        if (event.session.new) {
            // New Session
            console.log("NEW SESSION")
        }

        switch (event.request.type) {

            case "LaunchRequest":
                // Launch Request
                console.log('LAUNCH REQUEST')
                context.succeed(
                    generateResponse(
                        buildSpeechletResponse('Welcome to Irori For Pathfinder, this is running on a deployed lambda function', true), {}
                    )
                )
                break;

            case "IntentRequest":
                // Intent Request
                console.log('INTENT REQUEST')

                switch (event.request.intent.name) {

                    case "GetStat":
                        console.log('Hit the MakeDrink request: ${JSON.stringify(event.request.intent.slots)}');
                        var payload = {
                        	"objectName": event.request.intent.slots.Object.value,
                        	"statName": event.request.intent.slots.Stat.value
                        };
                        var endpoint = rootUri + "/stat";
                        processGetRequest(context, endpoint, "GetStat", payload);

                        break;

                    default:
                        throw "Invalid intent"
                }

                break;

            case "SessionEndedRequest":
                // Session Ended Request
                console.log('SESSION ENDED REQUEST');
                break;

            default:
                context.fail('INVALID REQUEST TYPE: ${event.request.type}')

        }

    } catch (error) {
        context.fail('Exception: ${error}')
    }

}

// Helpers
processGetRequest = (context, endpoint, intent, payload) => {

	request({url: endpoint, method: "POST", json: payload}, function(error, response, body) {
		if(response.statusCode == 200) {
			var result = JSON.parse(body);

			switch(result.type) {
				case "STAT":
					context.succeed(
						generateResponse(
							buildSpeechletResponse('The ${data.statName} of a ${data.objectName} is ${data.value}', true), {}
						)
					);
				break;
				case "OBJECT_ERROR":
					context.succeed(
						generateResponse(
							buildSpeechletResponse('I don\'t know ${data.objectName}', true), {}
						)
					);
				break;
				case "STAT_ERROR":
					context.succeed(
						generateResponse(
							buildSpeechletResponse('I don\'t know the ${data.statName} of a ${data.objectName}', true), {}
						)
					);
				break;
				default:
					context.succeed(
						generateResponse(
							buildSpeechletResponse('Something went wrong', true), {}
						)
					);
				break;
			}
		} else {
			console.log('error: ' + response.statusCode);
			console.log(body);
		}
	});

    return;
}

buildSpeechletResponse = (outputText, shouldEndSession) => {

    return {
        outputSpeech: {
            type: "PlainText",
            text: outputText
        },
        shouldEndSession: shouldEndSession
    }

}

generateResponse = (speechletResponse, sessionAttributes) => {

    return {
        version: "1.0",
        sessionAttributes: sessionAttributes,
        response: speechletResponse
    }
}