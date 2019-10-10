const app = require('express')();
const http = require('http').Server(app);
const io = require('socket.io')(http);

const bids = {}; //TODO: should get bids per product from db instead

io.on("connection", socket => {
  let previousId;
  const safeJoin = currentId => {
    socket.leave(previousId);
    socket.join(currentId);
    previousId = currentId;
  };
  
  console.log('user joined');

  socket.on("getBid", bidId => {
    safeJoin(bidId);
    socket.emit("bid", bids[bidId]);
    console.log()
  });

  socket.on("addBid", bid => {
  console.log('adding bid');
    bids[bid.id] = bid;
    safeJoin(bid.id);
    io.emit("bids", Object.keys(bids));
    socket.emit("bid", bid);
  });

  socket.on("editBid", bid => {
    bids[bid.id] = bid;
    socket.to(bid.id).emit("bids", bid);
  });

  io.emit("bids", Object.keys(bids));
});

http.listen(4444);
