import { useState } from 'react'

function App() {

  const [originalURL, setOriginalURL] = useState('');
  const [shortLinkID, setShortLinkID] = useState('');
  const [isBadRequest, setIsBadRequest] = useState(false);

  const shortLinkURL = location.origin + "/" + shortLinkID;

  function handleOriginalURLChange(e: React.ChangeEvent<HTMLInputElement>) {
    setOriginalURL(e.target.value);
  }

  function handleCreateShortLink() {
    setIsBadRequest(false);
    fetch('/api/shortlinks',
    {
      method: "POST",
      headers: {"Content-Type": 'application/json'}, 
      body: JSON.stringify({"originalURL": originalURL})
    }).then((res) => {
      if (res.ok) {
        return res.json()
      }

      throw new Error();
    }).then(data => {
      setShortLinkID(data['id'])
    }).catch(() => {
      setIsBadRequest(true);
    })
  }

  return (
    <div className='w-screen h-screen flex flex-col'>
      <header className='flex justify-around py-2 bg-emerald-400'>
        <div className='logo text-2xl text-gray-50'>Shorter</div>
      </header>
      <main className='grow flex justify-center px-4'>
        <div className='flex flex-col w-full max-w-2xl h-full gap-8'>
          <div className='h-48'></div>
          <div className='flex gap-4'>
            <input value={originalURL} onChange={handleOriginalURLChange} className='ring-2 ring-offset-2 rounded ring-gray-300 shrink-0 grow focus-visible:outline-none focus-visible:ring-gray-600' type="text" name="" id="" />
            <button onClick={handleCreateShortLink} className='bg-emerald-400 text-gray-50 px-2 py-1 rounded hover:bg-emerald-300'>短縮</button>
          </div>
          <div hidden={shortLinkID === ''} className='ring-2 ring-emerald-200 rounded py-2 px-4'>
            <h1>作成した短縮URL</h1>
            <p>{shortLinkURL}</p>
          </div>
          <div hidden={!isBadRequest} className='ring-2 ring-red-200 rounded py-2 px-4'>
            <h1>Error</h1>
            <p>入力したURLが不正です。</p>
          </div>
        </div>
      </main>
    </div>
  )
}

export default App
