import './index.css';
import { Link, NavLink } from 'react-router-dom';

const Header = () => {
    return (
        <header className="landing-header">
            <div className="div-landing-header">
                <Link to="/" className="div-landing-logo">
                    <img src="logo.png" alt="logo" />
                </Link>
                <ul>
                    <i className="fa fa-map-marker" aria-hidden="true"></i>
                    <li><a href="">Местоположение</a></li>
                    <i className="fa fa-phone" aria-hidden="true"></i>
                    <li><a href="">Телефон</a></li>
                </ul>
            </div>
            <>
                <img className="fr-landing-header-logo-center" src="logo.png" alt="logo" />
                <h3 className="fr-landing-header-title">Sisters Art Store</h3>
                <h1 className="fr-landing-header-content">Правим всичко с много любов и внимание!</h1>
                <button className="fr-landing-header-button-header">Влез</button>
            </>
            <section className="landing-options">
                <article className="landing-options-option">
                    <h4>Етерични масла</h4>
                    <Link to="/login" className="landing-options-option-a"> Виж повече ..</Link>
                </article>
                <article className="landing-options-option">
                    <h4>Бижута от истински цветя</h4>
                    <Link to="/login" className="landing-options-option-a">Виж повече ..</Link>
                </article>
                <article className="landing-options-option">
                    <h4>Картички</h4>
                    <Link to="/login" className="landing-options-option-a">Виж повече ..</Link>
                </article>
            </section>
        </header>
    );
};

export default Header;