import './index.css';
import { Link } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/authentication';
import Footer from "../Landing-page/Footer"
import * as postsService from '../../services/postsService';

const AddPosts = ({
    history,
}) => {
    const { currentUser } = useContext(AuthContext);
    const onSubmitHandler = function (e) {
        e.preventDefault();
        const { name, author, imageURL } = e.target;

        postsService.create(currentUser.token,name.value, author.value, imageURL.value)
            .then(() => {
                history.push('/blog');
            })
    };

    return (
        <>
            <section className="login-page">
                <Link to="/home" className="login-logo">
                    <img src="logo.png" alt="logo" />
                </Link>
                <article className="add-post-wrapper">
                    <h2>Добави статия:</h2>
                    <form className="add-post-content" onSubmit={onSubmitHandler}>

                        <label htmlFor="name">Име</label>
                        <input type="text" name="name" />

                        <label htmlFor="author">Автор</label>
                        <input type="textarea" name="author" />

                        <label htmlFor="imageURL">Съдържание</label>
                        <input type="text" name="imageURL" />


                        <button type="submit" className="add-post-submit" value="">Създай статия </button>
                    </form>
                </article>
            </section>
            <Footer />
        </>
    );
}

export default AddPosts;