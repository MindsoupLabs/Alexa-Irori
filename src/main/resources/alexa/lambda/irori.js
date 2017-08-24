var http = require('http');
var querystring = require('querystring');

var ip = process.env.IRORI_SERVER;
var port = '8088';
var protocol = 'http:';
var rootUri = protocol + '//' + ip + ':' + port + '/irori/';

exports.handler = (event, context) => {

	var isSpellFinder = event.session.application.applicationId == process.env.IRORI_SPELLFINDER_ID;
    var skillName =  isSpellFinder ? 'Spell Finder' : 'Stat Finder';

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
						buildSpeechletResponse('Welcome to ' + skillName + ' For Pathfinder. What can I do for you?', false), {}
					)
				)
				break;

			case "IntentRequest":
				// Intent Request
				console.log('INTENT REQUEST')

				switch (event.request.intent.name) {
					case "GetStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						createStatRequest(context, event.request.intent, "OBJECT");
						break;
					case "GetSpellStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						createStatRequest(context, event.request.intent, "SPELL");
						break;
					case "GetMonsterStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						createStatRequest(context, event.request.intent, "MONSTER");
						break;
					case "GetItemStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						createStatRequest(context, event.request.intent, "ITEM");
						break;
					case "AMAZON.HelpIntent":
						var helpContent = isSpellFinder ? '' : ', monsters, or items,';
						context.succeed(
							generateResponse(
								buildSpeechletResponse(`${skillName} can look up stats of spells${helpContent} from the Pathfinder role playing game for you. Try asking for the range of magic missile.`, false), {}
							)
						);
						break;
					case "AMAZON.StopIntent":
						context.succeed(
							generateResponse(
								buildSpeechletResponse(``, true), {}
							)
						);
						break;
					case "AMAZON.CancelIntent":
						context.succeed(
							generateResponse(
								buildSpeechletResponse(``, true), {}
							)
						);
						break;
					default:
						throw "Invalid intent"
				}

				break;

			case "SessionEndedRequest":
				// Session Ended Request
				console.log(`SESSION ENDED REQUEST`);
				break;

			default:
				context.fail(`INVALID REQUEST TYPE: ${event.request.type}`)

		}

	} catch (error) {
		context.fail(`Exception: ${error}`)
	}

}

createStatRequest = (context, intent, objectType) => {
	var stat = intent.slots.Stat ? intent.slots.Stat.value : 'description';
	var payload = {
		"objectName": intent.slots.Object.value,
		"statName": stat,
		"objectType": objectType || "OBJECT"
	};
	processPostRequest(context, '/irori/stat', "GetStat", payload);
}

// Helpers
processPostRequest = (context, path, intent, payload) => {

	var options = {
		hostname: ip,
		protocol: protocol,
		port: port,
		method: 'POST',
		path: path,
		headers: {'Content-Type': 'application/json'}
	};

	console.log(JSON.stringify(options));

	var body = "";
	const req = http.request(options, (response) => {

		console.log(`STATUS: ${response.statusCode}`);

		response.on('data', (chunk) => {
			body += chunk;
			console.log(body);
		});

		response.on('end', () => {
			console.log(body);
			var result = JSON.parse(body);

			switch(result.type) {
				case "STAT":
					context.succeed(
						generateResponse(
							buildSpeechletResponse(`The ${result.statName} of a ${result.objectName} is: ${result.value}`, true), {}
						)
					);
				break;
				case "OBJECT_ERROR":
					context.succeed(
						generateResponse(
							buildSpeechletResponse(`I don't know what a ${result.objectName} is`, true), {}
						)
					);
				break;
				case "STAT_ERROR":
					context.succeed(
						generateResponse(
							buildSpeechletResponse(`I don't know the ${result.statName} of a ${result.objectName}`, true), {}
						)
					);
				break;
				case "INPUT_ERROR":
					context.succeed(
						generateResponse(
							buildSpeechletResponse(`Sorry, I didn't understand that`, true), {}
						)
					);
				break;
				default:
					context.succeed(
						generateResponse(
							buildSpeechletResponse(`Sorry, I didn't understand that`, true), {}
						)
					);
				break;
			}
		});
	});

	req.on('error', (e) => {
		console.log(`problem with request: ${e.message}`);
	});

	req.write(JSON.stringify(payload));
	req.end();

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