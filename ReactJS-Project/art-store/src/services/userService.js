import firebase from "../utils/firebase";

export const adminCheck = (user) => {
    const email = '123@abv.bg';
    if (user.email===email) {
        return true;
    } else {
        return false;
    }
}


export async function getAll() {
    const listAllUsers = (nextPageToken) => {
        firebase
          .auth()
          .listUsers(100, nextPageToken)
        //   .then((listUsersResult) => {
        //     listUsersResult.users.forEach((userRecord) => {
        //       console.log('user', userRecord.toJSON());
        //     });
        //     if (listUsersResult.pageToken) {
        //       // List next batch of users.
        //       listAllUsers(listUsersResult.pageToken);
        //     }
        //   })
          .catch((error) => {
            console.log('Error listing users:', error);
          });
      };
     return listAllUsers();
};

