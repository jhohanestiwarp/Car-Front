export interface User {
  email: string;
  name: string;
}

export interface UserLogin {
  email: string;
  password: string;
}

export interface UserRegister extends UserLogin {
  name: string;
}

export interface Token {
  user: User;
  token: string;
  refresh_token: string;
}
