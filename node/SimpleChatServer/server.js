var net = require('net')

var chatServer = net.createServer();
clientList = [];

chatServer.on('connection', function(client){
  client.name = client.remoteAddress + ':' + client.remotePort
  client.write('Hi ' + client.name + '!\n');

  clientList.push(client)

  client.on('data', function(data){
    console.log(client.name + " = " + data)
    broadcast(data,client);
})

  client.on('end', function(){
    clientList.splice(clientList.indexOf(client),1)
})

})

function broadcast (message, client){
  for (var i = 0 ; i<clientList.length; i+=1){
    if (client !== clientList[i]){
        clientList[i].write(client.name+' says '+message)
}}}

console.log('Chat Server has started.')
chatServer.listen(process.env.VCAP_APP_PORT || 3000, null);
