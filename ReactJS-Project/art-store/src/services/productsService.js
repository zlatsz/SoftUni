const url = 'https://reactjs-532f1.firebaseapp.com/products';

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