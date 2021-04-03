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

export async function getOne(id) {
    let data = await fetch(url+id+'.json');
    let response = await data.json();
    return response;
}

export const create = (idToken, category, productName, description, imageURL,price ) => {
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
    
    return  fetch((url+`.json?auth=${idToken}`), postInfo);
};

export const like = (idToken,productId, likes) => {
    return fetch(url+productId+`.json?auth=${idToken}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({likes})
    })
        .then(res => res.json());
}

export const edit = async (idToken,productId, category, name, description, imageURL,price ) => {
    const res = await fetch(url + productId + `.json?auth=${idToken}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ category, name, description, imageURL, price })
    });
    return await res.json();
}

export const deleteProduct = (idToken,productId) => {
    return fetch(url+productId+`.json?auth=${idToken}`, {
        method: 'DELETE',
    })
        .then(res => res.json());
}