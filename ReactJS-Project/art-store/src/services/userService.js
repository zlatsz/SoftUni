const url = 'https://reactjs-532f1-default-rtdb.firebaseio.com/orders/';

export const adminCheck = (user) => {
    const email = '123@abv.bg';
    if (user.email===email) {
        return true;
    } else {
        return false;
    }
}


export const create = (user) => {
  let order = {
      user
  };

  let postInfo = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(order)
  };
  
  return  fetch((url+'.json'), postInfo);
};


export async function getAll() {
    let data = await fetch(url +'.json');
    let response = await data.json();
    let all = [];
    if (response) {
        Object.keys(response).forEach(key => {
            all.push({ id: key, ...response[key] });
        });
    }
    return all;
};

