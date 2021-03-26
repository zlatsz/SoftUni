const url = 'https://reactjs-532f1-default-rtdb.firebaseio.com/products/';

export const getAll = (category = '') => {
    let productsUrl = url + ((category && category !== 'all') ? `?category=${category}` : '');

    return fetch(productsUrl)
        .then(res => res.json())
        .catch(error => console.log(error));
}

export const getOne = (productId) => {
    return fetch(`${url}/${productId}`)
        .then(res => res.json())
        .catch(error => console.log(error));
}

export const create = (category, productName, description, imageURL,price ) => {
    let product = {
        name: productName,
        description,
        imageURL,
        category,
        likes: 0,
        price
    };

    let postInfo = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(product)
    };
    
    return  fetch((url+'.json'), postInfo);
};