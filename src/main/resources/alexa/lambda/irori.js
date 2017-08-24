var http = require('http');
var querystring = require('querystring');

var ip = process.env.IRORI_SERVER;
var port = '8088';
var protocol = 'http:';
var rootUri = protocol + '//' + ip + ':' + port + '/irori/';

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
						buildSpeechletResponse('Welcome to Stat Finder For Pathfinder, you can ask any stats of Pathfinder monsters, spells, or items. What do you want to know about?', false), {}
					)
				)
				break;

			case "IntentRequest":
				// Intent Request
				console.log('INTENT REQUEST')

				switch (event.request.intent.name) {

					case "GetStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": event.request.intent.slots.Stat.value,
							"objectType": "OBJECT"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetSpellStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": event.request.intent.slots.Stat.value,
							"objectType": "SPELL"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetMonsterStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": event.request.intent.slots.Stat.value,
							"objectType": "MONSTER"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetItemStat":
						console.log(`Hit the GetStat request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": event.request.intent.slots.Stat.value,
							"objectType": "ITEM"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetFormula":
						console.log(`Hit the GetFormula request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Formula.value,
							"statName": "formula",
							"objectType": "FORMULA"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetInfo":
						console.log(`Hit the GetInfo request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": "description",
							"objectType": "OBJECT"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetSpellInfo":
						console.log(`Hit the GetInfo request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": "description",
							"objectType": "SPELL"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetMonsterInfo":
						console.log(`Hit the GetInfo request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": "description",
							"objectType": "MONSTER"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

						break;
					case "GetItemInfo":
						console.log(`Hit the GetInfo request: ${JSON.stringify(event.request.intent.slots)}`);
						var payload = {
							"objectName": event.request.intent.slots.Object.value,
							"statName": "description",
							"objectType": "ITEM"
						};
						processPostRequest(context, '/irori/stat', "GetStat", payload);

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