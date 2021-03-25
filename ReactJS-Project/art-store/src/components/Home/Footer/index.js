import './index.css';

const Footer = () => {
    return (
        <footer className="home-footer">
            <section className="home-footer-upper">

            </section>
            <section className="home-footer-bottom">
                <div className="home-footer-icons">
                    <a href="https://www.facebook.com/SistersArtOOD/" target="_blank" rel="noreferrer" className="home-footer-link">
                        <i className="fab fa-facebook-f">SistersArtStore</i></a>
                    <a href="https://www.facebook.com/groups/610448616214671/" target="_blank" rel="noreferrer" className="home-footer-link">
                        <i className="fab fa-facebook-f">EssentialOil</i></a>
                    <a href="https://www.doterra.com/" target="_blank" rel="noreferrer" className="home-footer-link">
                        <i className="fas fa-link">DoTerra</i></a>
                </div>
                <p>Copyright &copy; 2021 Sisters Art Store All Rights Reserved</p>
            </section>
        </footer>

    );
};

export default Footer;