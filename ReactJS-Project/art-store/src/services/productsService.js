const url = 'https://reactjs-532f1-default-rtdb.firebaseio.com/products/';

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

export async function getCategory(category){
    let urlJ = ('https://reactjs-532f1-default-rtdb.firebaseio.com/products.json') ;
    let productsUrl =(urlJ + `?orderBy="category"&equalTo="${category}"&print=pretty`);
    let data = await fetch(productsUrl);
    let response = await data.json();
    let all = [];
    if (response) {
        Object.keys(response).forEach(key => {
            all.push({ id: key, ...response[key] });
        });
    }
    return all;
};

// export const getCategory = (category = '') => {
//     let urlJ = ('https://reactjs-532f1-default-rtdb.firebaseio.com/products.json') ;
//     let productsUrl =(urlJ + `?orderBy="category"&equalTo="${category}"&print=pretty`);

//     return fetch(productsUrl)
//         .then(res => res.json())
//         .catch(error => console.log(error));
// };


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