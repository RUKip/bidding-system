export class Settings {
  readonly defaultUrl: string = window.location.hostname;
  readonly dockerSocket = 'unix:///var/run/docker.sock';
}

