
const url = 'https://reactjs-532f1-default-rtdb.firebaseio.com/orders/';



export async function create(idToken, userEmail) {
    let order = {
        userEmail
    };

    let postInfo = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(order)
    };
    let data = await fetch((url + `.json?auth=${idToken}`), postInfo);
    let response = await data.json();
    return response;
}



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

export async function getOrder(id) {
    let data = await fetch(url + id + '.json');
    let response = await data.json();
    let products = response.products;
    let all = [];
    if (products) {
        Object.keys(products).forEach(key => {
            all.push({ id: key, ...products[key] });
        });
    }
    return all;
}


export const deleteCart = (idToken, orderId) => {
    return fetch(url + orderId + `.json?auth=${idToken}`, {
        method: 'DELETE',
    })
        .then(res => res.json());
}

export const add = async (idToken, cartId, productId, productName, productPic,quantity, price) => {
    let totalPrice = Number(quantity*price);
    let order = {
            productId,
            productName,
            productPic,
            quantity,
            price,
            totalPrice
    };

     const res = await fetch(url + cartId + `/products/.json?auth=${idToken}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(order)
    });
    return await res.json();
}