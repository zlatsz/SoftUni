import { Link } from 'react-router-dom';
import { useEffect, useState, useContext } from 'react';
import Header from "../../Home/Navigation/Header";
import Footer from "../../Home/Footer";
import * as productsService from '../../../services/productsService';
import * as ordersService from '../../../services/ordersService';
import { AuthContext } from '../../../contexts/authentication';
import {CartContext} from '../../../contexts/shoppingCart';
import Modal from 'react-modal';
import "./index.css";

const ProductDetails = ({
    match,
    history

}) => {

    const { currentUser } = useContext(AuthContext);
    const { cart } = useContext(CartContext);
    const [product, setProduct] = useState({});

    useEffect(() => {
        productsService.getOne(match.params.productId)
            .then(res => setProduct(res))
            .catch((error) => alert(error.message));
    }, [match.params.productId]);

    const onLikeButtonClickHandler = () => {
        let incrementedLikes = product.likes + 1;
        productsService.like(currentUser.token,match.params.productId, incrementedLikes)
            .then((updatedProduct) => {
                setProduct(state => ({ ...state, likes: Number(updatedProduct.likes) }))
            })
            .catch((error) => alert(error.message));
    };

    const onSubmitHandler = function (e) {
        e.preventDefault();
        const productId = match.params.productId;
        const productName = product.name;
        const productPic = product.imageURL;
        const quantity = e.target.quantity.value;
        const price = product.price;
        ordersService.add(currentUser.token,cart.name,productId,productName,productPic,quantity,price)
        .then(() => {
            history.push(`/cart/${cart.name}`);
        })
        .catch((error) => alert(error.message));
    };

    let [isModalOpen, setModalOpen] = useState(false);

    const openModalHandler = function (e) {
        setModalOpen(true);
    }

    const closeModalHandler = function (e) {
        setModalOpen(false);
    }

    const deleteModalHandler = function (e) {
        productsService.deleteProduct(currentUser.token,match.params.productId)
            .then(() => {
                history.push('/home');
            })
            .catch((error) => alert(error.message));
    }

    const modalStyle = {
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)'
        }
    };

    return (
        <>
            <header className="home-header">
                <Header />
                <h3 className="home-header-category-title">
                    Всички наши продукти се изработват ръчно от естествени съставки и материали!
                     </h3>
            </header>

            <section className="detail-products-list-item">

                <article className="detail-products-list-item-img"><img src={product.imageURL} alt="product" /></article>
                <h3> {product.name}</h3>
                <p className="detail-products-list-item-description">Описание: {product.description}</p>
                <p className="detail-products-list-item-price">{product.price} лв.</p>

                <form className="detail-products-list-item-cart" onSubmit={onSubmitHandler}>
                    <label htmlFor="quantity">Количество</label>
                    <input type="number" name="quantity" defaultValue="1"/>

                    <button type="submit" className="detail-products-list-item-button" >Купи</button>
                </form>

                <p className="detail-products-list-item-likes"> {product.likes}
                    <button className="detail-products-list-item-likes-button" onClick={onLikeButtonClickHandler}>
                        <i className="fas fa-heart"></i>Харесай!
                        </button>
                </p>
                {currentUser.isAdmin &&
                <div className="detail-products-list-item-admin">
                    <Link to={`/product/edit/${match.params.productId}`}><button className="detail-products-list-item-likes-button">Редактирай</button></Link>
                    <button className="detail-products-list-item-button" onClick={openModalHandler}>Изтрий</button>
                    <Modal
                        isOpen={isModalOpen}
                        style={modalStyle}
                        ariaHideApp={false}
                        onRequestClose={closeModalHandler}>
                        <h1 className="modal-title">Потвърди, че искаш да го изтриеш!</h1>
                        <button className="detail-products-list-item-button" onClick={closeModalHandler}>Затвори</button>
                        <button className="detail-products-list-item-button" onClick={deleteModalHandler}>Изтрий</button>
                    </Modal>
                </div>
                }
            </section>
            <Footer />
        </>
    );
}

export default ProductDetails;