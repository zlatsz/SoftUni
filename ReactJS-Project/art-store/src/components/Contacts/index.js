import "./index.css";
import Footer from "../Landing-page/Footer";
import GoogleMap from "./GoogleMap";
import ContactForm from "./ContactForm";
import { Link} from 'react-router-dom';

const Contacts = () => {
    return (
        <>
            <section className="contact-page">
                <section className="logo-page">
                <Link to="/home" className="contact-logo">
                    <img src="logo.png" alt="logo" />
                </Link>
                </section>
               
                <section className="map-wrapper">
                    <GoogleMap />
                    <article className="map-text">
                        <h3>
                            Можете да ни намерите на адрес:
                            <br />
                            София, кв.Студетски град
                            <br />
                            <br />
                            Не се колебайте да ни посетите!
                        </h3>
                    </article>
                </section>
                <section className="contact-wrapper">
                    <article className="contact-text">
                        <h3>
                            Имате въпроси!
                            <br />
                            Свържете се с нас на посочения телефон 
                            <br />
                            или чрез контактната форма ={">"}
                        </h3>
                    </article>
                    <ContactForm />

                </section>
            </section>
            <Footer />
        </>
    );
};


export default Contacts;