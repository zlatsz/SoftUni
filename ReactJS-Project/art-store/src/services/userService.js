
export const adminCheck = (user) => {
    const email = '123@abv.bg';
    if (user.email===email) {
        return true;
    } else {
        return false;
    }
}

