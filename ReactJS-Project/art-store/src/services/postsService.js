const url = 'https://reactjs-532f1-default-rtdb.firebaseio.com/posts/';

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

export async function getOne(id) {
    let data = await fetch(url+id+'.json');
    let response = await data.json();
    return response;
}

export const create = (idToken, postName, author, imageURL) => {
    let post = {
        name: postName,
        author,
        imageURL
    };

    let postInfo = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(post)
    };
    
    return  fetch((url+`.json?auth=${idToken}`), postInfo);
};

export const deletePost = (idToken,productId) => {
    return fetch(url+productId+`.json?auth=${idToken}`, {
        method: 'DELETE',
    })
        .then(res => res.json());
}