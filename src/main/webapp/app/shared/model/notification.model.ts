import { IUser } from 'app/core/user/user.model';

export interface INotification {
  id?: number;
  titre?: string;
  client?: IUser;
}

export class Notification implements INotification {
  constructor(public id?: number, public titre?: string, public client?: IUser) {}
}
