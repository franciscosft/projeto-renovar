'use strict';

const amqplib = require('amqplib');

const RABBITMQ_USER = process.env.RABBITMQ_USER || 'admin';
const RABBITMQ_PASS = process.env.RABBITMQ_PASS || 'admin';
const RABBITMQ_HOST = process.env.RABBITMQ_HOST || 'localhost';
const AMQP_URL      = `amqp://${RABBITMQ_USER}:${RABBITMQ_PASS}@${RABBITMQ_HOST}:5672`;
const EXCHANGE    = 'reading.exchange';
const ROUTING_KEY = 'reading.key';
const INTERVAL_MS = 60_000;

function randomFloat(min, max) {
  return parseFloat((Math.random() * (max - min) + min).toFixed(2));
}

function buildPayload() {
  return {
    deviceId:    1,
    indicatorId: 1,
    value:       randomFloat(15.0, 35.0),
  };
}

async function startSimulator() {
  const connection = await amqplib.connect(AMQP_URL);
  const channel    = await connection.createChannel();

  await channel.assertExchange(EXCHANGE, 'topic', { durable: true });

  console.log(`[simulator] Connected to ${AMQP_URL}`);
  console.log(`[simulator] Publishing to exchange "${EXCHANGE}" every ${INTERVAL_MS / 1000}s\n`);

  function publish() {
    const payload = buildPayload();
    const body    = Buffer.from(JSON.stringify(payload));

    channel.publish(EXCHANGE, ROUTING_KEY, body, { contentType: 'application/json', persistent: true });

    console.log(`[${new Date().toISOString()}] Published → value: ${payload.value}`);
  }

  publish();
  setInterval(publish, INTERVAL_MS);

  process.on('SIGINT', async () => {
    console.log('\n[simulator] Shutting down...');
    await channel.close();
    await connection.close();
    process.exit(0);
  });
}

startSimulator().catch((err) => {
  console.error('[simulator] Fatal error:', err.message);
  process.exit(1);
});