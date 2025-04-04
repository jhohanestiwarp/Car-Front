export interface HTTPBaseResponse {
  message: string;
  status: boolean;
}

export interface HTTPResponse<T = any> extends HTTPBaseResponse {
  data: T;
}
