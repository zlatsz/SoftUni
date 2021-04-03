import './index.css';
import { Link } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/authentication';
import Footer from "../Landing-page/Footer"
import * as productsService from '../../services/productsService';

function AddProducts({
    history,
}) {
    const { currentUser } = useContext(AuthContext);
    const onSubmitHandler = function (e) {
        e.preventDefault();
        const { category, name, description, imageURL, price } = e.target;

        productsService.create(currentUser.token,category.value, name.value, description.value, imageURL.value, price.value)
            .then(() => {
                history.push('/home');
            });
    };
    

    return (
        <>
            <section className="login-page">
                <Link to="/home" className="login-logo">
                    <img src={process.env.PUBLIC_URL + '/logo.png'} alt="logo" />
                </Link>
                <article className="add-product-wrapper">
                    <h2>Добави продукт:</h2>
                    <form className="add-product-content" onSubmit={onSubmitHandler}>
                        <label htmlFor="category">Категория</label>
                        <select type="text" name="category" id="category">
                            <option value="jewelleries"> Бижута </option>
                            <option value="cosmetics"> Козметика</option>
                            <option value="carts"> Картички </option>
                            <option value="others"> Други </option>
                        </select>

                        <label htmlFor="name">Име</label>
                        <input type="text" name="name"  />

                        <label htmlFor="description">Описание</label>
                        <input type="textarea" name="description"  />

                        <label htmlFor="imageURL">Снимка</label>
                        <input type="text" name="imageURL"  />

                        <label htmlFor="price">Цена</label>
                        <input type="number" name="price" />

                        <button type="submit" className="add-product-submit" value="">Създай продукт</button>
                    </form>
                </article>
            </section>
            <Footer />
        </>
    );
}

export default AddProducts;