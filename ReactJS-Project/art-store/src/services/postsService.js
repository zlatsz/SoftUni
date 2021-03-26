const url = 'https://reactjs-532f1-default-rtdb.firebaseio.com/posts/';

export const getAll = () => {

    return fetch(url)
        .then(res => res.json())
        .catch(error => console.log(error));
}

export const getOne = (postId) => {
    return fetch(`${url}/${postId}`)
        .then(res => res.json())
        .catch(error => console.log(error));
}

export const create = (postName, author, imageURL) => {
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
    
    return  fetch((url+'.json'), postInfo);
};