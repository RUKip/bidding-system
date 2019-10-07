import {SocketIoConfig} from 'ngx-socket-io';

export class Settings {
  readonly defaultUrl: string = window.location.hostname;
  readonly dockerSocket = 'unix:///var/run/docker.sock';
  readonly socketConfig: SocketIoConfig = { url: 'http://' + window.location.hostname , options: {} };
}

