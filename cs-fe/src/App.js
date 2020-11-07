import "./App-tailwind.css";

function App() {
  return (
    <form action="/encrypt" method="POST">
      <div className="App">
        <div className="table-auto px-2 py-2">
          <div className="table-row-group shadow-md">
            <div className="table-row">
              <div className="table-cell"></div>
              <div className="table-cell uppercase text-4xl">
                Crypto Square!
              </div>
            </div>
            <div className="table-row">
              <div className="table-cell text-right text-2xl px-3 py-2">
                Plaintext
              </div>
              <div className="table-cell">
                <input
                  id="plaintext"
                  className="bg-gray-200 px-1 shadow-inner text-2xl"
                  name="plaintext"
                  size="30"
                  type="text"
                />
              </div>
              <div className="table-cell align-middle px-2">
                <button className="px-2 text-2xl text-white transition duration-500 ease-in-out bg-blue-500 hover:bg-indigo-500 transform">
                  Go!
                </button>
              </div>
            </div>
            <div className="table-row">
              <div className="table-cell text-2xl">Ciphertext</div>
              <div className="table-cell text-2xl px-3 py-2">
                <input
                  id="ciphertext"
                  className="bg-gray-200 px-1 cursor-not-allowed shadow-inner"
                  name="ciphertext"
                  size="30"
                  type="text"
                  placeholder="[Your encoded plaintext appears here]"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default App;
