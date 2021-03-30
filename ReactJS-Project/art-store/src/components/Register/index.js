import "./index.css"
import { useState } from 'react'
import { Link, useHistory } from 'react-router-dom';
import firebase from '../../firebase';
import Footer from "../Landing-page/Footer"

const Register = () => {
    const history = useHistory();

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [repeatPassword, setRepeatPassword] = useState('');

    const signUp = (e) => {
        e.preventDefault()

        firebase.auth()
            .createUserWithEmailAndPassword(email, password)
            .catch((error) => alert(error.message))

        history.push('/home')
    }

        return (
            <>
                <section className="register-page">
                    <Link to="/" className="register-logo">
                        <img src="logo.png" alt="logo" />
                    </Link>
                    <article className="register-page-wrapper">
                        <h2>Регистрирай се:</h2>
                        <form className="register-page-content" onSubmit={signUp}>
                            <i className="fas fa-envelope fa-lg" aria-hidden="true"></i>
                            <input type="text" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} />

                            <i className="fa fa-lock fa-lg" aria-hidden="true"></i>
                            <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} />

                            <i className="fa fa-unlock fa-lg" aria-hidden="true"></i>
                            <input type="password" placeholder="Repeat Password" value={repeatPassword} onChange={(e) => setRepeatPassword(e.target.value)} />

                            <button type="submit" className="register-page-submit" value="">Влез</button>
                        </form>
                        <h3>Ако си регистриран влез от <Link to="/login"><span>ТУК</span></Link></h3>
                    </article>
                </section>
                <Footer />
            </>
        );
    };

export default Register;