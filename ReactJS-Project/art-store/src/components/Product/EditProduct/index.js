import { useEffect, useState, useContext } from 'react';
import { AuthContext } from '../../../contexts/authentication';
import * as productsService from '../../../services/productsService';
import { Link } from 'react-router-dom';
import Footer from "../../Landing-page/Footer";

const EditProduct = ({
    match,
    history

}) => {
    const { currentUser } = useContext(AuthContext);
    const [product, setProduct] = useState({});

    useEffect(() => {
        productsService.getOne(match.params.productId)
            .then(res => setProduct(res));
    }, [match.params.productId]);

    const onEditSubmitHandler = function (e) {
        e.preventDefault();
        const { category, name, description, imageURL, price} = e.target;

        productsService.edit(currentUser.token,match.params.productId,category.value, name.value, description.value, imageURL.value, price.value)
            .then(() => {
                history.push(`/product/details/${match.params.productId}`);
            })
    };
    
    return (
            <>
            <section className="login-page">
                <Link to="/home" className="login-logo">
                    <img src={process.env.PUBLIC_URL + '/logo.png'} alt="logo" />
                </Link>
                <article className="add-product-wrapper">
                    <h2>Съществуващ продукт</h2>
                    <form className="add-product-content" onSubmit={onEditSubmitHandler}>
                        <label htmlFor="category">Категория</label>
                        <select type="text" name="category" id="category" value={product.category} onChange={(e) => setProduct(e.target.value)} >
                            <option value="jewelleries"> Бижута </option>
                            <option value="cosmetics"> Козметика</option>
                            <option value="carts"> Картички </option>
                            <option value="others"> Други </option>
                        </select>

                        <label htmlFor="name">Име</label>
                        <input type="text" name="name" value={product.name} onChange={(e) => setProduct(e.target.value)} />

                        <label htmlFor="description">Описание</label>
                        <input type="textarea" name="description" value={product.description} onChange={(e) => setProduct(e.target.value)}/>

                        <label htmlFor="imageURL">Снимка</label>
                        <input type="text" name="imageURL" value={product.imageURL}  onChange={(e) => setProduct(e.target.value)}/>

                        <label htmlFor="price">Цена</label>
                        <input type="number" name="price" value={product.price} onChange={(e) => setProduct(e.target.value)}/>

                        <button type="submit" className="add-product-submit" value="">Редактирай</button>
                    </form>
                </article>
            </section>
            <Footer />
        </>
    );
}

export default EditProduct;