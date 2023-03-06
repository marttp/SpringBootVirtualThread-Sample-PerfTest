import http from 'k6/http';
import { sleep } from 'k6';

const services = {
  norm: {
    name: 'sb-norm',
    port: 8082,
  },
  vt: {
    name: 'sb-vt',
    port: 8080,
  },
};

export const options = {
  vus: 10,
  duration: '10s',
};

function test_case_1(service, port) {
  http.get(`http://${service}:${port}/case1?slowtime=1000`);
  sleep(1);
}

function test_case_2(i) {
  http.get(`http://sb-downstream:8081/${i}`);
  sleep(1);
}

function test_case_3(service, port) {
  http.get(`http://${service}:${port}/case3?slowtime=1`);
  sleep(1);
}

export default function () {
  const { norm, vt } = services;
  // test_case_1(norm.name, norm.port);
  // test_case_1(vt.name, vt.port);
  // test_case_2(0);
  // test_case_2(1);
  // test_case_3(norm.name, norm.port);
  test_case_3(vt.name, vt.port);
}
