import Header from "../../Home/Navigation/Header";
import Footer from "../../Home/Footer";
import * as userService from '../../../services/userService';
import { useEffect, useState} from 'react';
import Message from "./Message";

const Messages = () => {
   
    const [message, setMessage] = useState({});

    useEffect(() => {
        userService.getAll()
            .then(res => setMessage(res))
            .catch((error) => alert(error.message));
    }, []);

    let result = Array.from(message);
  
    return (
        <>
            <header className="home-header">
                <Header />
                <h2 className="blog-header-category-title">
                    Това са всички получени съобщения:
                     </h2>
            </header>
            <section className="users-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>
                                Име
                </th>
                            <th>
                                Съобщения
                </th>
                        </tr>
                    </thead>
                    <tbody>
                        {result.map(x =>
                            <Message key={x.id} {...x} />
                        )}
                    </tbody>
                </table>

            </section>
            <Footer />
        </>
    );

};

export default Messages;