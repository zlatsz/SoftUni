import Modal from 'react-modal';
import Header from "../../Home/Navigation/Header";
import Footer from "../../Home/Footer";
import { useEffect, useState } from 'react';
import * as postsService from '../../../services/postsService';
import "./index.css"

const PostDetails = ({
    match,
    history
}) => {

    const [post, setPost] = useState({});

    useEffect(() => {
        postsService.getOne(match.params.postId)
            .then(res => setPost(res));
    }, [match.params.postId]);

    const [isModalOpen, setModalOpen] = useState(false);

    const openModalHandler = function (e) {
        setModalOpen(true);
    }

    const closeModalHandler = function (e) {
        setModalOpen(false);
    }

    const deleteModalHandler = function (e) {
        postsService.deletePost(match.params.productId)
            .then(() => {
                history.push('/blog');
            })
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
                <h2 className="blog-header-category-title">
                    ЕТЕРИЧНИТЕ МАСЛА И ТЯХНОТО ВЛИЯНИЕ ЗА ЖИВОТ БЕЗ БОЛЕСТ
                     </h2>
            </header>
            <section className="blog-post-article">
                <div className="blog-post-article_text">
                    <h2>{post.name}</h2>
                </div>
                <div className="blog-post-image">
                    <img className="blog-post-article_image" src={post.imageURL} alt="post" />
                </div>
                <div className="blog-post-article_text">
                    <h4>Автор: {post.author}</h4>
                </div>
                <div className="detail-products-list-item-admin">
                    <button className="detail-products-list-item-button blog-btn" onClick={openModalHandler}>Изтрий</button>
                    <Modal
                        isOpen={isModalOpen}
                        style={modalStyle}
                        onRequestClose={closeModalHandler}>
                        <h1 className="modal-title">Потвърди, че искаш да го изтриеш!</h1>
                        <button className="detail-products-list-item-button" onClick={closeModalHandler}>Затвори</button>
                        <button className="detail-products-list-item-button" onClick={deleteModalHandler}>Изтрий</button>
                    </Modal>
                </div>
            </section>
            <Footer />
        </>

    );

};

export default PostDetails;