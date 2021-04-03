const url = 'https://reactjs-532f1-default-rtdb.firebaseio.com/orders/';

export const create = (idToken, userEmail) => {
    let order = {
        userEmail
    };

    let postInfo = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(order)
    };

    return fetch((url +`.json?auth=${idToken}`), postInfo);
};


export async function getAll() {
    let data = await fetch(url + '.json');
    let response = await data.json();
    let all = [];
    if (response) {
        Object.keys(response).forEach(key => {
            all.push({ id: key, ...response[key] });
        });
    }
    return all;
};

export async function getOne(id) {
    let data = await fetch(url + id + '.json');
    let response = await data.json();
    return response;
}


export const deleteCart = (orderId) => {
    return fetch(url + orderId + '.json', {
        method: 'DELETE',
    })
        .then(res => res.json());
}